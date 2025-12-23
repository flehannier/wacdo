import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CollaborateurForm } from './collaborateur-form';


describe('CollaborateurForm', () => {
  let component: CollaborateurForm;
  let fixture: ComponentFixture<CollaborateurForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollaborateurForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollaborateurForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
