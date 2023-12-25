import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCcInformationComponent } from './add-cc-information.component';

describe('AddCcInformationComponent', () => {
  let component: AddCcInformationComponent;
  let fixture: ComponentFixture<AddCcInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCcInformationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCcInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
