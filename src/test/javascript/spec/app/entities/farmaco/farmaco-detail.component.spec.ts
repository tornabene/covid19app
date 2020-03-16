import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Covid19AppTestModule } from '../../../test.module';
import { FarmacoDetailComponent } from 'app/entities/farmaco/farmaco-detail.component';
import { Farmaco } from 'app/shared/model/farmaco.model';

describe('Component Tests', () => {
  describe('Farmaco Management Detail Component', () => {
    let comp: FarmacoDetailComponent;
    let fixture: ComponentFixture<FarmacoDetailComponent>;
    const route = ({ data: of({ farmaco: new Farmaco(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [FarmacoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FarmacoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FarmacoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load farmaco on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.farmaco).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
