import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JenkinsFolderDetailsComponent } from './jenkins-folder-details.component';

describe('JenkinsFolderDetailsComponent', () => {
  let component: JenkinsFolderDetailsComponent;
  let fixture: ComponentFixture<JenkinsFolderDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JenkinsFolderDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JenkinsFolderDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
