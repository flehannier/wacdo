import { Component, OnInit } from '@angular/core';
import { GenericListComponent} from "../generic-list-component/generic-list-component";
import { CollaborateurEdit, CollaborateurList, CollaborateurModel } from '../../models/collaborateur-model';
import { CollaborateurService } from '../../services/collaborateur-service';
import { ListAction, ListColumn } from '../../models/list-model';
import { FieldsFormTypeEnum, FormField, SelectOption } from '../../models/FieldsForm';
import { Validators } from '@angular/forms';
import { FonctionService } from '../../services/fonction-service';
import { RestaurantService } from '../../services/restaurant-service';
import { forkJoin } from 'rxjs';
import { GenericModalComponent } from '../generic-modal-component/generic-modal-component';

@Component({
  selector: 'app-collaborateur-component',
  standalone: true,
  imports: [GenericListComponent, GenericModalComponent],
  templateUrl: './collaborateur-component.html',
  styleUrl: './collaborateur-component.css',
})
export class CollaborateurComponent implements OnInit{
  collaborateurs: CollaborateurList[] = [];
  item?: CollaborateurEdit;
  modalTitle: string = 'Collaborateur';
  formFields: FormField[] = []
  showModal = false;
  selectedItem: any = null;
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

  constructor(private collaborateurService: CollaborateurService, private fonctionService: FonctionService, private restaurantService: RestaurantService){
  }

  ngOnInit(){
    
    forkJoin({
      fonctions: this.fonctionService.listFonctions(),
      restaurants: this.restaurantService.listRestaurants()
    }).subscribe(({ fonctions, restaurants }) => {

      const optionsFonction: SelectOption[] = fonctions.map(f => ({ value: f.id, label: f.intitule }));
      const optionsRestaurant: SelectOption[] = restaurants.map(r => ({ value: r.id, label: r.nom }));

      this.formFields = [
        { key: 'id', label: 'Id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
        { key: 'nom', label: 'Nom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '', validators: [Validators.required] },
        { key: 'prenom', label: 'Prénom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validators: [Validators.required]  },
        { key: 'email', label: 'Email',  type: FieldsFormTypeEnum.EMAIL, disabled: false, required: true, placeholder: '',  validators: [Validators.email, Validators.required] },
        { key: 'motDePasse', label: 'Mot de passe',  type: FieldsFormTypeEnum.PASSWORD, disabled: false, required: false, placeholder: '' },
        { key: 'fonction', label: 'Fonction', type: FieldsFormTypeEnum.SELECT, options: optionsFonction ,disabled: false, required: true, placeholder: 'Choix d\'une fonction',  validators: [Validators.required]  },
        { key: 'restaurant', label: 'Restaurant', type: FieldsFormTypeEnum.SELECT, options: optionsRestaurant, disabled: false, required: true, placeholder: 'Choix du restaurant',  validators: [Validators.required]  }
        ]
      });
      
    this.load();
  }

  load(){
    this.collaborateurService.listCollaborateurs().subscribe({
      next: (data) => {
        this.collaborateurs = data.map((collab: CollaborateurModel) => {
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
    
    this.item = this.collaborateurs.find((collab: CollaborateurModel) => collab.id === item.id) as CollaborateurEdit; 
  
    if (this.item) {
      const lastAffectation = this.item.affectations?.[this.item.affectations.length - 1];
      this.selectedItem = {
        ...this.item,
        fonction: lastAffectation?.fonction,
        restaurant: lastAffectation?.restaurant
      };
    }
  
    console.log('Modifier:', this.selectedItem);
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

  createOrUpdate(item:CollaborateurEdit){
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
    this.selectedItem = null;
  }
}
