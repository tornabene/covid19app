import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Covid19AppTestModule } from '../../../test.module';
import { FarmacoComponent } from 'app/entities/farmaco/farmaco.component';
import { FarmacoService } from 'app/entities/farmaco/farmaco.service';
import { Farmaco } from 'app/shared/model/farmaco.model';

describe('Component Tests', () => {
  describe('Farmaco Management Component', () => {
    let comp: FarmacoComponent;
    let fixture: ComponentFixture<FarmacoComponent>;
    let service: FarmacoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [FarmacoComponent]
      })
        .overrideTemplate(FarmacoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FarmacoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FarmacoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Farmaco(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.farmacos && comp.farmacos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
