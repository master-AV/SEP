import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetCcInformationComponent } from './get-cc-information.component';

describe('GetCcInformationComponent', () => {
  let component: GetCcInformationComponent;
  let fixture: ComponentFixture<GetCcInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetCcInformationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetCcInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
