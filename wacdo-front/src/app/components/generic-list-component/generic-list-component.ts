import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListAction, ListColumn } from '../../models/list-model';
import { FormsModule } from '@angular/forms';
import { SortEnum } from '../../enums/sort-enum';

@Component({
  selector: 'app-generic-list-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './generic-list-component.html',
  styleUrl: './generic-list-component.css',
})

export class GenericListComponent<T> {
  @Input() title: string = 'Liste';
  @Input() data: T[] = [];
  @Input() columns: ListColumn[] = [];
  @Input() actions: ListAction[] = [];
  @Input() searchable: boolean = true;
  @Input() paginated: boolean = true;
  @Input() pageSize: number = 10;
  @Input() striped: boolean = true;
  @Input() showAddButton: boolean = true;
  @Input() addButtonLabel: string = 'Ajouter';
  @Input() emptyMessage: string = 'Aucune donnée disponible';
  @Input() showCount: boolean = true;

  @Output() addClicked = new EventEmitter<void>();
  @Output() searchChanged = new EventEmitter<string>();

  searchTerm: string = '';
  filteredData: T[] = [];
  paginatedData: T[] = [];
  currentPage: number = 1;
  sortColumn: string = '';
  sortDirection: SortEnum = SortEnum.ASC;

  ngOnInit() {
    this.filteredData = this.data;
    this.updatePagination();
  }

  ngOnChanges() {
    this.filteredData = this.data;
    this.onSearch();
  }

  onSearch() {
    if (!this.searchTerm) {
      this.filteredData = this.data;
    } else {
      this.filteredData = this.data.filter(item =>
        this.columns.some(column => {
          const value = this.getNestedProperty(item, column.key);
          return value?.toString().toLowerCase().includes(this.searchTerm.toLowerCase());
        })
      );
    }
    this.currentPage = 1;
    this.updatePagination();
    this.searchChanged.emit(this.searchTerm);
  }

  onSort(columnKey: string) {
    this.sortDirection = this.sortDirection === SortEnum.ASC ? SortEnum.DESC : SortEnum.ASC;

    this.filteredData.sort((a, b) => {
      const aValue = this.getNestedProperty(a, columnKey);
      const bValue = this.getNestedProperty(b, columnKey);

      if (aValue < bValue) return this.sortDirection === SortEnum.ASC ? -1 : 1;
      if (aValue > bValue) return this.sortDirection === SortEnum.ASC ? 1 : -1;
      return 0;
    });

    this.updatePagination();
  }

  onAdd() {
    this.addClicked.emit();
  }

  getNestedProperty(obj: any, path: string): any {
    return path.split('.').reduce((prev, curr) => {
      if (prev === null || prev === undefined) {
        return undefined;
      }
      
      // Si prev est un tableau, prendre le dernier élément
      if (Array.isArray(prev)) {
        return prev.length > 0 ? prev[prev.length - 1]?.[curr] : undefined;
      }
      
      return prev[curr];
    }, obj);
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

  get totalPages(): number {
    return Math.ceil(this.filteredData.length / this.pageSize);
  }

  get startIndex(): number {
    return (this.currentPage - 1) * this.pageSize;
  }

  get endIndex(): number {
    return Math.min(this.startIndex + this.pageSize, this.filteredData.length);
  }

  updatePagination() {
    if (this.paginated) {
      const start = this.startIndex;
      const end = this.endIndex;
      this.paginatedData = this.filteredData.slice(start, end);
    } else {
      this.paginatedData = this.filteredData;
    }
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePagination();
    }
  }
}
