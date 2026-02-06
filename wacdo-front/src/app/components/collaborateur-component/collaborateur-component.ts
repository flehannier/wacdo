import { Component, OnInit } from '@angular/core';
import { GenericListComponent} from "../generic-list-component/generic-list-component";
import { CollaborateurModel, CollaborateurRequest} from '../../models/collaborateur-model';
import { CollaborateurService } from '../../services/collaborateur-service';
import { ListAction, ListColumn } from '../../models/list-model';
import { FieldsFormTypeEnum, FormField, SelectOption } from '../../models/FieldsForm';
import { Validators } from '@angular/forms';
import { FonctionService } from '../../services/fonction-service';
import { RestaurantService } from '../../services/restaurant-service';
import { forkJoin } from 'rxjs';
import { GenericModalComponent } from '../generic-modal-component/generic-modal-component';
import { AuthService } from '../../services/auth-service';
import { RoleService } from '../../services/role-service';

@Component({
  selector: 'app-collaborateur-component',
  standalone: true,
  imports: [GenericListComponent, GenericModalComponent],
  templateUrl: './collaborateur-component.html',
  styleUrl: './collaborateur-component.css',
})
export class CollaborateurComponent implements OnInit{
  collaborateurs: CollaborateurModel[] = [];
  item?: CollaborateurModel;
  modalTitle: string = 'Collaborateur';
  formFields: FormField[] = []
  showModal = false;
  selectedItem?: CollaborateurModel;
  errors: string = '';

  columns: ListColumn[] = [
    { key: 'id', label: 'Id', sortable: true },
    { key: 'nom', label: 'Nom', sortable: true },
    { key: 'prenom', label: 'Prénom', sortable: true },
    { key: 'email', label: 'Email', sortable: true, width: '250px' },
    { key: 'fonction.nom', label: 'Fonction', sortable: true },
    { key: 'restaurant.nom', label: 'Restaurant', sortable: true },
    { key: 'role.nom', label: 'Role', sortable: true }
  ];

  actionsList: ListAction[] = [
    {
      label: 'Modifier',
      color: 'primary',
      callback: (item) => this.onEdit(item)
    },
    {
      label: 'Supprimer',
      color: 'danger',
      callback: (item) => this.onDelete(item)
    }
  ];

  modalAction!: ListAction;

  constructor(private authService: AuthService, private roleService: RoleService, private collaborateurService: CollaborateurService, private fonctionService: FonctionService, private restaurantService: RestaurantService){
  }

  ngOnInit(){
    
    this.load();
    
    this.roleService.listRoles().subscribe((roles ) => {
      const optionsRole: SelectOption[] = roles.map(r => ({ value: r.id, label: r.name }));
      this.formFields = [
          { key: 'id', label: 'Id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
          { key: 'nom', label: 'Nom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '', validators: [Validators.required] },
          { key: 'prenom', label: 'Prénom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validators: [Validators.required]  },
          { key: 'email', label: 'Email',  type: FieldsFormTypeEnum.EMAIL, disabled: false, required: true, placeholder: '',  validators: [Validators.email, Validators.required] },
          { key: 'motDePasse', label: 'Mot de passe',  type: FieldsFormTypeEnum.PASSWORD, disabled: false, required: false, placeholder: '' },
          { key: 'role.id', label: 'Role', type: FieldsFormTypeEnum.SELECT, options: optionsRole ,disabled: false, required: true, placeholder: 'Choix d\'un role',  validators: [Validators.required]  },
        ]
    });
  }

  load(){
    this.collaborateurService.listCollaborateurs().subscribe({
      next: (data: CollaborateurModel[]) => {
        this.collaborateurs = data
                              .filter((collab: CollaborateurModel) => collab.email != this.authService.getUsername() )
                              .map((collab: CollaborateurModel) => {

          console.log('LISTE = '+ JSON.stringify( {
            ...collab,
            fonction: collab?.fonction,
            restaurant: collab?.restaurant
          }));
          
          return {
            ...collab,
            fonction: collab?.fonction,
            restaurant: collab?.restaurant
          };
        });
      },
      error: (error) => {
        console.error('Erreur:', error);
      }
    });
  }

  onEdit(item: CollaborateurModel) {
    this.showModal = true;
    this.modalTitle = 'Modifier un collaborateur';
    this.modalAction = 
    {
      label: 'Modifier',
      color: 'primary',
      callback: (data) => this.createOrUpdate(data)
    };
    
    this.selectedItem = this.collaborateurs.find((collab: CollaborateurModel) => collab.id === item.id); 
  }

  onAddCollaborateur() {
    this.showModal = true;
    this.modalTitle = 'Ajouter un collaborateur';
    this.selectedItem = undefined;
    this.modalAction = 
    {
      label: 'Ajouter',
      color: 'primary',
      callback: (data) => this.createOrUpdate(data)
    };
  }

  onDelete(item: CollaborateurModel) {
   this.collaborateurService.delete(item.id)?.subscribe({
      next: () => {
        this.load();
      },
      error: (err) => {
        this.errors = err.error.message;
      }
   });
  }

  onSearchChanged(term: string) {
    console.log('Recherche:', term);
  }

  createOrUpdate(item:any){
      if(!item.motDePasse){
        delete item['motDePasse']
      }    

      const request: CollaborateurRequest = {
        id: item.id,
        nom : item.nom,
        prenom: item.prenom,  
        email: item.email,
        motDePasse: item.motDePasse, 
        datePremiereEmbauche: item.datePremiereEmbauche,         
        administrateur: item.administrateur,  
        roleId: item['role.id']?.id                   
      }

      this.collaborateurService.save(request).subscribe({
        next: () => {
          this.showModal = false;
          this.load();
        },
        error: (err) => {
          this.errors = err.error.message;
        }
      });
  }

  closeModal() {
    this.showModal = false;
    this.selectedItem = undefined;
  }
}
