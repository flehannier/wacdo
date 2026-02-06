import { Component } from '@angular/core';
import { GenericListComponent } from '../generic-list-component/generic-list-component';
import { GenericModalComponent } from '../generic-modal-component/generic-modal-component';
import { ListAction, ListColumn } from '../../models/list-model';
import { AuthService } from '../../services/auth-service';
import { RestaurantService } from '../../services/restaurant-service';
import { FieldsFormTypeEnum, FormField } from '../../models/FieldsForm';
import { RestaurantModel } from '../../models/restaurant-model';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-restaurant-component',
  standalone: true,
  imports: [GenericListComponent, GenericModalComponent],
  templateUrl: './restaurant-component.html',
  styleUrl: './restaurant-component.css',
})
export class RestaurantComponent {
  restaurants: RestaurantModel[] = [];
  item?: RestaurantModel;
  modalTitle: string = 'Restaurant';
  formFields: FormField[] = []
  showModal = false;
  selectedItem?: RestaurantModel;
  errors: string = '';

  columns: ListColumn[] = [
    { key: 'id', label: 'Id', sortable: true },
    { key: 'nom', label: 'Nom', sortable: true },
    { key: 'adresse', label: 'Adresse', sortable: true },
    { key: 'codePostal', label: 'Code postal', sortable: true, width: '250px' },
    { key: 'ville', label: 'Ville', sortable: true }
  ];

  actionsList: ListAction[] = [
    {
      label: 'Modifier',
      color: 'primary',
      callback: (item) => this.onEdit(item)
    }
  ];

  modalAction!: ListAction;

  constructor(private authService: AuthService, private restaurantService: RestaurantService){
  }
  
  ngOnInit(){
    
    this.load();

    this.formFields = [
      { key: 'id', label: 'Id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
      { key: 'nom', label: 'Nom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '', validators: [Validators.required] },
      { key: 'adresse', label: 'adresse', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validators: [Validators.required]  },
      { key: 'codePostal', label: 'Code postal',  type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validators: [Validators.required, Validators.required] },
      { key: 'ville', label: 'Ville',  type: FieldsFormTypeEnum.TEXT, disabled: false, required: false, placeholder: '' },
    ]
  }

  load(){
    this.restaurantService.listRestaurants().subscribe({
      next: (data) => {
        this.restaurants = data
      },
      error: (error) => {
        console.error('Erreur:', error);
      }
    });
  }

  onAddRestaurant() {
    this.showModal = true;
    this.modalTitle = 'Ajouter un restaurant';
    this.selectedItem = undefined;
    this.modalAction = 
    {
      label: 'Ajouter',
      color: 'primary',
      callback: (data) => this.save(data)
    };
  }
  
  onEdit(item: RestaurantModel) {
    this.showModal = true;
    this.modalTitle = 'Modifier un restaurant';
    this.modalAction = 
    {
      label: 'Modifier',
      color: 'primary',
      callback: (data) => this.save(data)
    };
    
    this.selectedItem = this.restaurants.find((rest: RestaurantModel) => rest.id === item.id); 
  }

  save(item:RestaurantModel){
      
      this.restaurantService.save(item).subscribe({
        next: () => {
          this.showModal = false;
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

  closeModal() {
    this.showModal = false;
    this.selectedItem = undefined;
  }
}
