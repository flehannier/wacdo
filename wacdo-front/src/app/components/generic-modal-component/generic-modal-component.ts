import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ListAction } from '../../models/list-model';
import { FormField, FieldsFormTypeEnum} from '../../models/FieldsForm';
import type { ModalAction } from '../../models/FieldsForm';

@Component({
  selector: 'app-generic-modal-component',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './generic-modal-component.html',
  styleUrl: './generic-modal-component.css',
})

export class GenericModalComponent implements OnInit, OnChanges {
  @Input() errors?: string | null;
  @Input() show: boolean = false;
  @Input() title: string = '';
  @Input() fields: FormField[] = [];
  @Input() item: any = null;
  @Input() action!: ModalAction;
  @Output() close = new EventEmitter<void>();
  @Output() isEdit = new EventEmitter<void>();
  @Input() showMessage: boolean = false;
  isEditMode = false;

  form!: FormGroup;
  FieldsFormTypeEnum = FieldsFormTypeEnum;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.errors = null;
    this.buildForm();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['item']) {
      if (this.item && this.item.id) {
        console.log('✅ MODE ÉDITION ACTIVÉ - ID:', this.item.id);
        this.isEditMode = true;
        this.isEdit.emit();
        setTimeout(() => this.patchFormValues(), 0);
      } else {
        console.log('➕ MODE CRÉATION ACTIVÉ');
        this.isEditMode = false;
        if (this.form) {
          this.form.reset();
        }
      }
    }
  }

  buildForm() {
    const formControls: any = {};

    this.fields.forEach(field => {
      const validators = [];

      // Ajouter les validateurs
      if (field.required) {
        validators.push(Validators.required);
      }

      if (field.type === FieldsFormTypeEnum.EMAIL) {
        validators.push(Validators.email);
      }

      // Validateurs personnalisés du field
      if (field.validators) {
        validators.push(...field.validators);
      }

      // Utilisation de l'instance FormControl pour plus de contrôle
      formControls[field.key] = [{ value: '', disabled: field.disabled }, validators];
    });

    this.form = this.fb.group(formControls);

    // Patcher les valeurs si item existe
    if (this.item) {
      this.patchFormValues();
    }
  }

  getValue(obj: any, path: string) {
    if (!obj || !path) return '';

    // On découpe le chemin (ex: "restaurant.nom" devient ["restaurant", "nom"])
    // Et on réduit l'objet étape par étape
    return path.split('.').reduce((acc, part) => acc && acc[part], obj);
  }

  patchFormValues() {
    const patchData: any = {};

    this.fields.forEach(field => {
      // Si la clé contient un point (ex: 'collaborateur.id')
      if (field.key.includes('.')) {
        const parts = field.key.split('.');
        // On extrait la valeur de l'objet imbriqué : item['collaborateur']['id']
        const value = parts.reduce((acc, part) => acc && acc[part], this.item);
        patchData[field.key] = value;
      } else {
        patchData[field.key] = this.item[field.key];
      }
    });

    this.form.patchValue(patchData);
  }

  closeModal() {
    this.show=false;
    this.errors = null;
    this.item = null;
    this.form.reset();
    this.close.emit();
  }

  onSubmit() {
    if (this.form.valid) {
      const formData = this.getFormData();
      this.form.reset();
      this.action.callback(formData);
    } else {
      // Marquer tous les champs comme touchés pour afficher les erreurs
      Object.keys(this.form.controls).forEach(key => {
        this.form.controls[key].markAsTouched();
      });
    }
  }

  getFormData(): any {
    const rawValue = this.form.getRawValue(); // Inclut les champs disabled
    const formattedData: any = {};

    this.fields.forEach(field => {
      let value = rawValue[field.key];

      // Reconvertir en objet pour les SELECT
      if (field.type === FieldsFormTypeEnum.SELECT && value) {
        value = { id: value };
      }

      formattedData[field.key] = value;
    });

    // Garder l'ID si c'est une mise à jour
    if (this.item?.id) {
      formattedData.id = this.item.id;
    }

    return formattedData;
  }

  getActionClass(color?: string): string {
    const baseClasses = 'focus:ring-4';
    switch (color) {
      case 'primary':
        return `${baseClasses} bg-sky-500 hover:bg-sky-600 focus:ring-sky-500/50`;
      case 'danger':
        return `${baseClasses} bg-red-500 hover:bg-red-600 focus:ring-red-500/50`;
      case 'success':
        return `${baseClasses} bg-green-500 hover:bg-green-600 focus:ring-green-500/50`;
      case 'warning':
        return `${baseClasses} bg-yellow-500 hover:bg-yellow-600 focus:ring-yellow-500/50`;
      default:
        return `${baseClasses} bg-gray-500 hover:bg-gray-600 focus:ring-gray-500/50`;
    }
  }

  // Helper pour vérifier si un champ a une erreur
  hasError(fieldKey: string): boolean {
    const control = this.form.get(fieldKey);
    return !!(control && control.invalid && control.touched);
  }

  // Helper pour obtenir le message d'erreur
  getErrorMessage(fieldKey: string): string {
    const control = this.form.get(fieldKey);
    if (!control || !control.errors) return '';

    if (control.errors['required']) {
      return 'Ce champ est requis';
    }
    if (control.errors['email']) {
      return 'Email invalide';
    }
    if (control.errors['min']) {
      return `Valeur minimale : ${control.errors['min'].min}`;
    }
    if (control.errors['max']) {
      return `Valeur maximale : ${control.errors['max'].max}`;
    }

    return 'Valeur invalide';
  }
}
