export interface ListColumn {
  key: string;
  label: string;
  sortable?: boolean;
  width?: string;
}

export interface ListAction {
  label: string;
  icon?: string;
  color?: 'primary' | 'danger' | 'success' | 'warning';
  callback: (item: any) => void;
}
