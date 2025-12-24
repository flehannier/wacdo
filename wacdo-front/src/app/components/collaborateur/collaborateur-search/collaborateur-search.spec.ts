import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollaborateurSearch } from './collaborateur-search';

describe('CollaborateurSearch', () => {
  let component: CollaborateurSearch;
  let fixture: ComponentFixture<CollaborateurSearch>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollaborateurSearch]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollaborateurSearch);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
