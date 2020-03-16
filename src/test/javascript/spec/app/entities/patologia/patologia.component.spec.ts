import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Covid19AppTestModule } from '../../../test.module';
import { PatologiaComponent } from 'app/entities/patologia/patologia.component';
import { PatologiaService } from 'app/entities/patologia/patologia.service';
import { Patologia } from 'app/shared/model/patologia.model';

describe('Component Tests', () => {
  describe('Patologia Management Component', () => {
    let comp: PatologiaComponent;
    let fixture: ComponentFixture<PatologiaComponent>;
    let service: PatologiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [PatologiaComponent]
      })
        .overrideTemplate(PatologiaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatologiaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatologiaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Patologia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.patologias && comp.patologias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
