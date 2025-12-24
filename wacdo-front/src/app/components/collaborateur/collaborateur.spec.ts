import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Collaborateur } from './collaborateur';

describe('Collaborateur', () => {
  let component: Collaborateur;
  let fixture: ComponentFixture<Collaborateur>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Collaborateur]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Collaborateur);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
