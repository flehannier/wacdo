import { Component } from '@angular/core';
import { CollaborateurModel, CollaborateurRequest } from '../../models/collaborateur-model';
import { FieldsFormTypeEnum, FormField, SelectOption } from '../../models/FieldsForm';
import { Validators } from '@angular/forms';
import { ListAction, ListColumn } from '../../models/list-model';
import { AuthService } from '../../services/auth-service';
import { RoleService } from '../../services/role-service';
import { CollaborateurService } from '../../services/collaborateur-service';
import { FonctionService } from '../../services/fonction-service';
import { RestaurantService } from '../../services/restaurant-service';
import { GenericModalComponent } from '../generic-modal-component/generic-modal-component';

@Component({
  selector: 'app-profile-component',
  standalone: true,
  imports: [GenericModalComponent],
  templateUrl: './profile-component.html',
  styleUrl: './profile-component.css',
})
export class ProfileComponent {
  item?: CollaborateurModel;
  modalTitle = 'Modifier son profile';
  formFields: FormField[] = []
  showModal = false;
  selectedItem?: any;
  errors: string = '';

  modalAction: ListAction = 
  {
    label: 'Modifier',
    color: 'primary',
    callback: (item: any) => this.update(item)
  };
    

  constructor(private authService: AuthService, private roleService: RoleService, private collaborateurService: CollaborateurService, private fonctionService: FonctionService, private restaurantService: RestaurantService){
  }

  ngOnInit(){
    this.showModal = true;
        
    this.roleService.listRoles().subscribe(roles => {

       this.collaborateurService.getByUsername(this.authService.getUsername())
        .subscribe({
            next: (data) => {
              this.selectedItem = data;
              
              this.selectedItem = {
                ... data,
                roleId:  data.role?.id,
              };
            },
            error: (err) => {
              console.error('Erreur lors de la récupération du collaborateur', err);
            }
        });
        
      const optionsRole: SelectOption[] = roles.map( (r) => ({ value: r.id, label: r.name }));
      this.formFields = [
          { key: 'id', label: 'Id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
          { key: 'nom', label: 'Nom', type: FieldsFormTypeEnum.TEXT, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
          { key: 'prenom', label: 'Prénom', type: FieldsFormTypeEnum.TEXT, disabled: true, required: true, placeholder: '',  validators: [Validators.required]  },
          { key: 'email', label: 'Email',  type: FieldsFormTypeEnum.EMAIL, disabled: true, required: true, placeholder: '',  validators: [Validators.email, Validators.required] },
          { key: 'motDePasse', label: 'Mot de passe',  type: FieldsFormTypeEnum.PASSWORD, disabled: false, required: false, placeholder: '' },
          { key: 'roleId', label: 'Role', type: FieldsFormTypeEnum.SELECT, options: optionsRole ,disabled: false, required: true, placeholder: 'Choix d\'un role',  validators: [Validators.required]  },
        ]
        
    });
    
  }

  update(item:any){
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
        roleId: item.roleId                   
      }

      this.collaborateurService.save(request).subscribe({
        next: () => {
          this.showModal = false;
        },
        error: (err) => {
          this.errors = err.error.message;
        }
      });
  }

  closeModal() {
    this.selectedItem = undefined;
  }
}
