import { Component } from '@angular/core';
import { GenericListComponent } from "../generic-list-component/generic-list-component";
import { ListAction, ListColumn } from '../../models/list-model';
import { AffectationModel } from '../../models/affectation-model';
import { AffectationService } from '../../services/affectation-service';
import { GenericModalComponent } from '../generic-modal-component/generic-modal-component';
import { FieldsFormTypeEnum, FormField, SelectOption } from '../../models/FieldsForm';
import { FonctionService } from '../../services/fonction-service';
import { RestaurantService } from '../../services/restaurant-service';
import { CollaborateurService } from '../../services/collaborateur-service';
import { forkJoin } from 'rxjs';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-affectation-component',
  standalone: true,
  imports: [GenericListComponent, GenericModalComponent],
  templateUrl: './affectation-component.html',
  styleUrl: './affectation-component.css',
})
export class AffectationComponent {
  item?: AffectationModel;
  modalTitle: string = 'Affectation';
  formFields: FormField[] = []
  showModal = false;
  selectedItem: any = null;
  affectations: AffectationModel[] = [];
  errors: string = '';

  columns: ListColumn[] = [
    { key: 'id', label: 'Id', sortable: true },
    { key: 'dateDebut', label: 'Date de début', sortable: true },
    { key: 'dateFin', label: 'Date de fin', sortable: true },
    { key: 'collaborateur.nom', label: 'Collaborateur', sortable: true, width: '250px' },
    { key: 'fonction.nom', label: 'Fonction', sortable: true },
    { key: 'restaurant.nom', label: 'Restaurant', sortable: true }
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

  modalAction: ListAction = 
    {
      label: 'Ajouter',
      color: 'primary',
      callback: (item) => this.createOrUpdate(item)
    };

  constructor(private affectationService: AffectationService,private collaborauerService: CollaborateurService, private fonctionService: FonctionService, private restaurantService: RestaurantService){

  }

  ngOnInit(){
    forkJoin({
          collaborauers: this.collaborauerService.listCollaborateurs(),
          fonctions: this.fonctionService.listFonctions(),
          restaurants: this.restaurantService.listRestaurants()
        }).subscribe(({ collaborauers, fonctions, restaurants }) => {
    
          const optionsCollaborateur: SelectOption[] = collaborauers.map(f => ({ value: f.id, label: f.nom }));
          const optionsFonction: SelectOption[] = fonctions.map(f => ({ value: f.id, label: f.intitule }));
          const optionsRestaurant: SelectOption[] = restaurants.map(r => ({ value: r.id, label: r.nom }));
     
      this.formFields = [
        { key: 'id', label: 'Id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
        { key: 'dateDebut', label: 'Date début', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '', validators: [Validators.required] },
        { key: 'dateFin', label: 'Date fin', type: FieldsFormTypeEnum.TEXT, disabled: false, required: false, placeholder: '',  validators: []  },
        { key: 'collaborateur', label: 'Collaborateur', type: FieldsFormTypeEnum.SELECT, options: optionsCollaborateur ,disabled: false, required: true, placeholder: 'Choix d\'un collaborateur',  validators: [Validators.required]  },
        { key: 'fonction', label: 'Fonction', type: FieldsFormTypeEnum.SELECT, options: optionsFonction ,disabled: false, required: true, placeholder: 'Choix d\'une fonction',  validators: [Validators.required]  },
        { key: 'restaurant', label: 'Restaurant', type: FieldsFormTypeEnum.SELECT, options: optionsRestaurant, disabled: false, required: true, placeholder: 'Choix d\'un restaurant',  validators: [Validators.required]  }
        ]
      });
      
    this.load();
  }

  load(){
    this.affectationService.listAffectations().subscribe({
      next: (data) => {
        this.affectations = data
      },
      error: (error) => {
        console.error('Erreur:', error);
      }
    });
  }

  onAddAffectation() {
    this.showModal = true;
    this.modalTitle = 'Ajouter une affectation';
    this.selectedItem = null;
      this.modalAction = 
      {
        label: 'Ajouter',
        color: 'primary',
        callback: (data) => this.createOrUpdate(data)
      };
  }

  onEdit(item: any) {  
      this.showModal = true;
      this.modalTitle = 'Modifier une affectation';
      this.modalAction = 
      {
        label: 'Modifier',
        color: 'primary',
        callback: (data) => this.createOrUpdate(data)
      };
      
      this.selectedItem = this.affectations.find((aff: AffectationModel) => aff.id === item.id) as AffectationModel; 
     
      console.log('Modifier:', this.selectedItem);
  }

  onDelete(item: AffectationModel) {
   this.affectationService.delete(item.id)?.subscribe({
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

  createOrUpdate(item: AffectationModel){
      item.dateDebut = new Date(item.dateDebut);
      if(item.dateFin) {
       item.dateFin = new Date(item.dateFin);
      }

      this.affectationService.save(item).subscribe({
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
