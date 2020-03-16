import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Covid19AppTestModule } from '../../../test.module';
import { SintomoDetailComponent } from 'app/entities/sintomo/sintomo-detail.component';
import { Sintomo } from 'app/shared/model/sintomo.model';

describe('Component Tests', () => {
  describe('Sintomo Management Detail Component', () => {
    let comp: SintomoDetailComponent;
    let fixture: ComponentFixture<SintomoDetailComponent>;
    const route = ({ data: of({ sintomo: new Sintomo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [SintomoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SintomoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SintomoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sintomo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sintomo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
