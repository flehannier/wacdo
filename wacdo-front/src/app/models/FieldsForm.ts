import { ValidatorFn } from '@angular/forms';

export type SelectOption = {
    value: any,
    label: string
}

export enum FieldsFormTypeEnum {
  TEXT = 'text',
  EMAIL = 'email',
  DATE = 'date',
  SELECT = 'select',
  HIDDEN = 'hidden',
  NUMBER = 'number',
  TEXTAREA = 'textarea',
  PASSWORD = 'password'
}

export interface FormField {
  key: string;
  label?: string;
  type: FieldsFormTypeEnum;
  required?: boolean;
  disabled?: boolean;
  placeholder?: string;
  options?: { value: any; label: string }[];
  validators?: any[];
}

export interface ModalAction {
  label: string;
  color?: 'primary' | 'danger' | 'success' | 'warning';
  callback: (data: any) => void;
}