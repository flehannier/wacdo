import { ValidatorFn } from '@angular/forms';

export type FieldsForm = {
    key: string,
    label: string,
    type: FieldsFormTypeEnum,
    required: boolean,
    placeholder: string,
    disabled: boolean,
    options?: SelectOption[]; // pour select
    validator?: ValidatorFn | ValidatorFn[];
}

export type SelectOption = {
    value: any,
    label: string
}

export enum FieldsFormTypeEnum{
    TEXT = 'text',
    SELECT = 'select',
    EMAIL = 'email',
    DATE = 'date',
    PASSWORD = 'password',
}