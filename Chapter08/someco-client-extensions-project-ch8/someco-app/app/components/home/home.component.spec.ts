import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { BBCComponent } from './home.component';
import { CoreModule } from 'ng2-alfresco-core';

describe('BBCComponent', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [CoreModule],
        declarations: [BBCComponent],
        schemas: [CUSTOM_ELEMENTS_SCHEMA]
    });
  });

  it ('should work', () => {
    let fixture = TestBed.createComponent(BBCComponent);
    expect(fixture.componentInstance instanceof BBCComponent).toBe(true, 'should create BBCComponent');
  });
});
