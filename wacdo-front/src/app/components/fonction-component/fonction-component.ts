import { Component, EventEmitter, Output, Input} from '@angular/core';
import { GenericListComponent } from "../generic-list-component/generic-list-component";
import { FonctionModel } from '../../models/fonction-model';
import { FonctionService } from '../../services/fonction.service';
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
  @Input() messageShow: boolean = false;

  columns: ListColumn[] = [
    { key: 'intitule', label: 'Intitule', sortable: true }
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
      { key: 'intitule', label: 'Intitulé', type: FieldsFormTypeEnum.TEXT, disabled: false, required: true, placeholder: '',  validators: [Validators.required]  },
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
    this.selectedItem = null;
    this.modalTitle = 'Ajouter une fonction';
    this.modalAction =
    {
      label: 'Ajouter',
      color: 'primary',
      callback: (data) => this.createOrUpdate(data)
    };
  }

  onEdit(item: any) {
    this.selectedItem = this.fonctions.find((aff: FonctionModel) => aff.id === item.id) as FonctionModel;
    this.modalTitle = 'Modifier une fonction';
    this.modalAction =
    {
      label: 'Modifier',
      color: 'primary',
      callback: (data) => this.createOrUpdate(data)
    };

    this.showModal = true;
  }

  onDelete(item: FonctionModel) {
    if(!item.id){
       this.errors = 'Aucun identifant de fonction passé en paramètre';
      return false
    }
    this.fonctionService.delete(item.id)?.subscribe({
        next: () => {
         this.selectedItem =  {};
         this.errors = "";
         this.load();
          return true
        },
        error: (err) => {
          this.errors = err.error.message;
          return false
        }
    });
    return true
  }

  createOrUpdate(item:any){
    this.fonctionService.save(item).subscribe({
       next: () => {
          this.messageShow = true;
          setTimeout(() => { this.closeModal() }, 2000);
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
    this.messageShow = false;
    this.selectedItem =  {};
    this.showModal= false;
    this.load();
  }
}
