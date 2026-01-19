import { Component } from '@angular/core';
import { GenericListComponent } from "../generic-list-component/generic-list-component";
import { FonctionModel } from '../../models/fonction-model';
import { FonctionService } from '../../services/fonction-service';
import { ListAction, ListColumn } from '../../models/list-model';
import { FieldsFormTypeEnum, FormField } from '../../models/FieldsForm';
import { GenericModalComponent } from '../generic-modal-component/generic-modal-component';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-fonction-component',
  standalone: true,
  imports: [GenericListComponent, GenericModalComponent],
  templateUrl: './fonction-component.html',
  styleUrl: './fonction-component.css',
})
export class FonctionComponent {
  item?: FonctionModel;
  modalTitle: string = 'Fonction';
  formFields: FormField[] = []
  showModal = false;
  selectedItem: any = null;
  fonctions: FonctionModel[] = [];
  errors: string = '';

  columns: ListColumn[] = [
    { key: 'id', label: 'Id', sortable: true },
    { key: 'intitule', label: 'Intitule', sortable: true }
  ];

  actions: ListAction[] = [
    {
      label: 'Modifier',
      color: 'primary',
      callback: (item) => this.onEdit(item)
    }
  ];

  modalAction: ListAction = 
  {
    label: 'Ajouter',
    color: 'primary',
    callback: (item) => this.createOrUpdate(item)
  };

  constructor(private fonctionService: FonctionService){

  }

  ngOnInit(){
    
    this.formFields = [
      { key: 'id', label: 'Id', type: FieldsFormTypeEnum.HIDDEN, disabled: true, required: true, placeholder: '', validators: [Validators.required] },
      { key: 'intitule', label: 'Intitule', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validators: [Validators.required]  },
      ]
    
    this.load();
  }

  load(){
   this.fonctionService.listFonctions().subscribe({
      next: (data) => {
        this.fonctions = data
      },
      error: (err) => {
        console.error('Erreur:', err);
          this.errors = err.error.message;
      }
    });
  }

  onAddFonction() {
    this.showModal = true;
    this.modalTitle = 'Ajouter une fonction';
    this.modalAction = 
    {
      label: 'Ajouter',
      color: 'primary',
      callback: (data) => this.createOrUpdate(data)
    };
  }

  onEdit(item: any) { 
    this.showModal = true;
    this.modalTitle = 'Modifier une fonction';
    this.modalAction = 
    {
      label: 'Modifier',
      color: 'primary',
      callback: (data) => this.createOrUpdate(data)
    };
    
    this.selectedItem = this.fonctions.find((aff: FonctionModel) => aff.id === item.id) as FonctionModel; 
    
    console.log('Modifier:', this.selectedItem);
  }

  onDelete(item: any) {
    console.log('Supprimer:', item);
  }

  createOrUpdate(item:any){
    this.fonctionService.save(item).subscribe({
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
    this.selectedItem = null;
  }
}
