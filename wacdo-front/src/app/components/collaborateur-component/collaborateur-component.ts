import { Component, OnInit } from '@angular/core';
import { GenericListComponent} from "../generic-list-component/generic-list-component";
import { CollaborateurList, CollaborateurModel } from '../../models/collaborateur-model';
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
  collaborateurs: CollaborateurList[] = [];
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
    { key: 'affectations.fonction.nom', label: 'Fonction', sortable: true },
    { key: 'affectations.restaurant.nom', label: 'Restaurant', sortable: true }
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
    
    forkJoin({
      fonctions: this.fonctionService.listFonctions(),
      restaurants: this.restaurantService.listRestaurants(),
      roles: this.roleService.listRoles()
    }).subscribe(({ fonctions, restaurants, roles }) => {

      const optionsRole: SelectOption[] = roles.map(r => ({ value: r.id, label: r.name }));
      const optionsFonction: SelectOption[] = fonctions.map(f => ({ value: f.id, label: f.intitule }));
      const optionsRestaurant: SelectOption[] = restaurants.map(r => ({ value: r.id, label: r.nom }));

      this.formFields = [
        { key: 'id', label: 'Id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
        { key: 'nom', label: 'Nom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '', validators: [Validators.required] },
        { key: 'prenom', label: 'Prénom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validators: [Validators.required]  },
        { key: 'email', label: 'Email',  type: FieldsFormTypeEnum.EMAIL, disabled: false, required: true, placeholder: '',  validators: [Validators.email, Validators.required] },
        { key: 'motDePasse', label: 'Mot de passe',  type: FieldsFormTypeEnum.PASSWORD, disabled: false, required: false, placeholder: '' },
        { key: 'role', label: 'Role', type: FieldsFormTypeEnum.SELECT, options: optionsRole ,disabled: false, required: true, placeholder: 'Choix d\'un role',  validators: [Validators.required]  },
        { key: 'fonction', label: 'Fonction', type: FieldsFormTypeEnum.HIDDEN, options: optionsFonction ,disabled: true, required: true, placeholder: 'Choix d\'une fonction',  validators: [Validators.required]  },
        { key: 'restaurant', label: 'Restaurant', type: FieldsFormTypeEnum.HIDDEN, options: optionsRestaurant, disabled: true, required: true, placeholder: 'Choix du restaurant',  validators: [Validators.required]  }
        ]
      });
      
    this.load();
  }

  load(){
    this.collaborateurService.listCollaborateurs().subscribe({
      next: (data) => {
        this.collaborateurs = data
                              .filter((collab: CollaborateurModel) => collab.email != this.authService.getUsername() )
                              .map((collab: CollaborateurModel) => {

          // Prendre la dernière affectation
          const lastAffectation = collab.affectations?.[collab.affectations.length - 1];
          
          return {
            ...collab,
            fonction: lastAffectation?.fonction?.intitule || 'Non affecté',
            restaurant: lastAffectation?.restaurant?.nom || 'Non affecté'
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
    console.log(this.collaborateurs);
    this.modalAction = 
    {
      label: 'Modifier',
      color: 'primary',
      callback: (data) => this.createOrUpdate(data)
    };
    
    this.selectedItem = this.collaborateurs.find((collab: CollaborateurModel) => collab.id === item.id); 
    

    console.log('Modifier:', this.selectedItem);
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
        console.error('Erreur:', err);
        this.errors = err.error.message;
      }
   });
  }

  onSearchChanged(term: string) {
    console.log('Recherche:', term);
  }

  createOrUpdate(item:CollaborateurModel){
      if(!item.motDePasse){
        delete item['motDePasse']
      }    
      this.collaborateurService.save(item).subscribe({
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
