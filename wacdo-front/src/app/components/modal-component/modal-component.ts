import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FieldsForm, FieldsFormTypeEnum } from '../../models/FieldsForm';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ListAction } from '../../models/list-model';

@Component({
  selector: 'app-modal-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './modal-component.html',
  styleUrl: './modal-component.css',
})
export class ModalComponent{
  
  // Enum pour template
  readonly FieldsFormTypeEnum = FieldsFormTypeEnum;
  @Input() openModal = false;
  @Output() openModalChange = new EventEmitter<boolean>();
  @Input() item?:  any;
  @Input() fields:  FieldsForm[] = [];
  @Input() title: string = 'Modal';
  @Input() action!: ListAction;

  show: boolean = false;
  
  ngOnChanges(changes: SimpleChanges) {
    if (changes['openModal']) {
      this.show = this.openModal;
    }
  }

  getNestedProperty(obj: any, path: string): any {
    return path.split('.').reduce((prev, curr) =>
      prev ? (Array.isArray(prev) ? prev.at(-1)?.[curr] : prev[curr]) : undefined
    , obj);
  }

  closeModal() {
    this.show = false;
    this.openModalChange.emit(false);
  }
  
  getActionClass(color?: string): string {
    const baseClasses = 'hover:opacity-80';
    switch (color) {
      case 'primary': return `${baseClasses} bg-sky-500 text-white`;
      case 'danger': return `${baseClasses} bg-red-500 text-white`;
      case 'success': return `${baseClasses} bg-green-500 text-white`;
      case 'warning': return `${baseClasses} bg-yellow-500 text-white`;
      default: return `${baseClasses} bg-gray-200 text-gray-700`;
    }
  }
}
