import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Covid19AppTestModule } from '../../../test.module';
import { PatologiaDetailComponent } from 'app/entities/patologia/patologia-detail.component';
import { Patologia } from 'app/shared/model/patologia.model';

describe('Component Tests', () => {
  describe('Patologia Management Detail Component', () => {
    let comp: PatologiaDetailComponent;
    let fixture: ComponentFixture<PatologiaDetailComponent>;
    const route = ({ data: of({ patologia: new Patologia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Covid19AppTestModule],
        declarations: [PatologiaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PatologiaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatologiaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load patologia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patologia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
