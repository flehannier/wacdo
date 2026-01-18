import { Component, OnInit } from '@angular/core';
import { GenericListComponent} from "../generic-list-component/generic-list-component";
import { CollaborateurEdit, CollaborateurList, CollaborateurModel } from '../../models/collaborateur-model';
import { CollaborateurService } from '../../services/collaborateur-service';
import { ListAction, ListColumn } from '../../models/list-model';
import { ModalComponent } from "../modal-component/modal-component";
import { FieldsForm, FieldsFormTypeEnum, SelectOption } from '../../models/FieldsForm';
import { Validators } from '@angular/forms';
import { FonctionService } from '../../services/fonction-service';
import { RestaurantService } from '../../services/restaurant-service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-collaborateur-component',
  imports: [GenericListComponent, ModalComponent],
  templateUrl: './collaborateur-component.html',
  styleUrl: './collaborateur-component.css',
})
export class CollaborateurComponent implements OnInit{
  collaborateurs: CollaborateurList[] = [];
  openModal: boolean = false;
  item?: CollaborateurEdit;
  modalTitle: string = 'Collaborateur';
  fields: FieldsForm[] = []

  columns: ListColumn[] = [
    { key: 'id', label: 'Id', sortable: true },
    { key: 'nom', label: 'Nom', sortable: true },
    { key: 'prenom', label: 'Prénom', sortable: true },
    { key: 'email', label: 'Email', sortable: true, width: '250px' },
    { key: 'fonction', label: 'Fonction', sortable: true },
    { key: 'restaurant', label: 'Restaurant', sortable: true }
  ];

  actions: ListAction[] = [
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

  constructor(private collaborateurService: CollaborateurService, private fonctionService: FonctionService, private restaurantService: RestaurantService){
  }

  ngOnInit(){
    
    forkJoin({
      fonctions: this.fonctionService.listFonctions(),
      restaurants: this.restaurantService.listRestaurants()
    }).subscribe(({ fonctions, restaurants }) => {

      const optionsFonction: SelectOption[] = fonctions.map(f => ({ value: f.id, label: f.intitule }));
      const optionsRestaurant: SelectOption[] = restaurants.map(r => ({ value: r.id, label: r.nom }));

      this.fields = [
        { key: 'nom', label: 'Nom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '', validator: [Validators.required] },
        { key: 'prenom', label: 'Prénom', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validator: [Validators.required]  },
        { key: 'email', label: 'Email',  type: FieldsFormTypeEnum.EMAIL, disabled: false, required: true, placeholder: '',  validator: [Validators.email, Validators.required] },
        { key: 'fonction', label: 'Fonction', type: FieldsFormTypeEnum.SELECT, options: optionsFonction ,disabled: false, required: true, placeholder: '',  validator: [Validators.required]  },
        { key: 'restaurant', label: 'Restaurant', type: FieldsFormTypeEnum.SELECT, options: optionsRestaurant, disabled: false, required: true, placeholder: '',  validator: [Validators.required]  }
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

  onAddCollaborateur() {
    this.openModal = true;
    this.modalTitle = 'Ajout d\'un collaborateur';
    console.log('Ajouter un nouveau collaborateur');
  }

  onEdit(item: CollaborateurModel) {
    this.openModal = true;
    this.modalTitle = 'Edition d\'un collaborateur';
    console.log(this.collaborateurs);

    this.item = this.collaborateurs.find((collab: CollaborateurModel) => collab.id === item.id) as CollaborateurEdit; 
  
    if (this.item) {
      const lastAffectation = this.item.affectations?.[this.item.affectations.length - 1];
      this.item = {
        ...this.item,
        fonction: lastAffectation?.fonction,
        restaurant: lastAffectation?.restaurant
      };
    }
  
    console.log('Modifier:', this.item);
  }


  onDelete(item: CollaborateurModel) {
   this.collaborateurService.delete(item.id)?.subscribe({
      next: () => {
        this.load();
      },
      error: (error) => {
        console.error('Erreur:', error);
      }
   });
  }

  onSearchChanged(term: string) {
    console.log('Recherche:', term);
  }
}
