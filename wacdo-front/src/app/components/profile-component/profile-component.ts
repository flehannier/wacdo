import { Component, EventEmitter, Input, Output } from '@angular/core';
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
  @Input() show: boolean = true;
  @Output() close = new EventEmitter<void>();

  modalAction: ListAction =
  {
    label: 'Modifier',
    color: 'primary',
    callback: (item: any) => {
      if (!item) return;
      this.update(item);
    }
  };


  constructor(private authService: AuthService, private roleService: RoleService, private collaborateurService: CollaborateurService, private fonctionService: FonctionService, private restaurantService: RestaurantService){
  }

  ngOnInit() {
    this.roleService.listRoles().subscribe(roles => {

      const optionsRole = roles.map(r => ({
        value: r.id,
        label: r.name
      }));

      this.collaborateurService
        .getByUsername(this.authService.getUsername())
        .subscribe(data => {

          this.selectedItem = {
            ...data,
            roleId: data.role?.id
          };

          this.formFields = [
            { key: 'id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true },
            { key: 'nom', label: 'Nom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true },
            { key: 'prenom', label: 'Prénom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true },
            { key: 'email', label: 'Email', type: FieldsFormTypeEnum.EMAIL, disabled: false, required: true },
            { key: 'motDePasse', label: 'Mot de passe', type: FieldsFormTypeEnum.PASSWORD },
            {
              key: 'roleId',
              label: 'Role',
              type: FieldsFormTypeEnum.SELECT,
              options: optionsRole,
              required: true,
              placeholder: 'Choix d’un rôle'
            }
          ];
        });
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
        roleId: item.roleId.id
      }

      this.collaborateurService.save(request).subscribe({
        next: () => {
          this.closeModal();
        },
        error: (err) => {
          this.errors = err.error.message;
        }
      });
  }

  ngOnChanges() {
    if (this.show) {
      this.showModal = true;
    }
  }

  closeModal() {
    this.showModal = false;
    this.close.emit();
  }
}
