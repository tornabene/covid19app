import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Covid19AppTestModule } from '../../../test.module';
import { PazienteDetailComponent } from 'app/entities/paziente/paziente-detail.component';
import { Paziente } from 'app/shared/model/paziente.model';

describe('Component Tests', () => {
  describe('Paziente Management Detail Component', () => {
    let comp: PazienteDetailComponent;
    let fixture: ComponentFixture<PazienteDetailComponent>;
    const route = ({ data: of({ paziente: new Paziente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [PazienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PazienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PazienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paziente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paziente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
