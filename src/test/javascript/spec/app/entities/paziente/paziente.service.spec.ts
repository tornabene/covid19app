import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PazienteService } from 'app/entities/paziente/paziente.service';
import { IPaziente, Paziente } from 'app/shared/model/paziente.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';

describe('Service Tests', () => {
  describe('Paziente Service', () => {
    let injector: TestBed;
    let service: PazienteService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaziente;
    let expectedResult: IPaziente | IPaziente[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PazienteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Paziente(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        Sex.M,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        false,
        false,
        false,
        false,
        false,
        false,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        0,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataTampone: currentDate.format(DATE_TIME_FORMAT),
            dataGuarito: currentDate.format(DATE_TIME_FORMAT),
            dataIsolamentoDomiciliare: currentDate.format(DATE_TIME_FORMAT),
            dataDeceduto: currentDate.format(DATE_TIME_FORMAT),
            dataTerapiaIntensiva: currentDate.format(DATE_TIME_FORMAT),
            dataDimissione: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Paziente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataTampone: currentDate.format(DATE_TIME_FORMAT),
            dataGuarito: currentDate.format(DATE_TIME_FORMAT),
            dataIsolamentoDomiciliare: currentDate.format(DATE_TIME_FORMAT),
            dataDeceduto: currentDate.format(DATE_TIME_FORMAT),
            dataTerapiaIntensiva: currentDate.format(DATE_TIME_FORMAT),
            dataDimissione: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataTampone: currentDate,
            dataGuarito: currentDate,
            dataIsolamentoDomiciliare: currentDate,
            dataDeceduto: currentDate,
            dataTerapiaIntensiva: currentDate,
            dataDimissione: currentDate
          },
          returnedFromService
        );

        service.create(new Paziente()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Paziente', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            cf: 'BBBBBB',
            age: 1,
            sex: 'BBBBBB',
            comune: 'BBBBBB',
            provincia: 'BBBBBB',
            regione: 'BBBBBB',
            latitudine: 'BBBBBB',
            longitudine: 'BBBBBB',
            numeroFamigliari: 1,
            professione: 'BBBBBB',
            fumatore: true,
            posivito: true,
            isolamentoDomiciliare: true,
            terapiaIntensiva: true,
            guarito: true,
            deceduto: true,
            dataTampone: currentDate.format(DATE_TIME_FORMAT),
            dataGuarito: currentDate.format(DATE_TIME_FORMAT),
            dataIsolamentoDomiciliare: currentDate.format(DATE_TIME_FORMAT),
            dataDeceduto: currentDate.format(DATE_TIME_FORMAT),
            dataTerapiaIntensiva: currentDate.format(DATE_TIME_FORMAT),
            dataDimissione: currentDate.format(DATE_TIME_FORMAT),
            giorniIsolamentoDomiciliare: 1,
            giorniTerapiaIntensiva: 1,
            giorniGuarito: 1,
            giorniDeceduto: 1,
            nota: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataTampone: currentDate,
            dataGuarito: currentDate,
            dataIsolamentoDomiciliare: currentDate,
            dataDeceduto: currentDate,
            dataTerapiaIntensiva: currentDate,
            dataDimissione: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Paziente', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            cf: 'BBBBBB',
            age: 1,
            sex: 'BBBBBB',
            comune: 'BBBBBB',
            provincia: 'BBBBBB',
            regione: 'BBBBBB',
            latitudine: 'BBBBBB',
            longitudine: 'BBBBBB',
            numeroFamigliari: 1,
            professione: 'BBBBBB',
            fumatore: true,
            posivito: true,
            isolamentoDomiciliare: true,
            terapiaIntensiva: true,
            guarito: true,
            deceduto: true,
            dataTampone: currentDate.format(DATE_TIME_FORMAT),
            dataGuarito: currentDate.format(DATE_TIME_FORMAT),
            dataIsolamentoDomiciliare: currentDate.format(DATE_TIME_FORMAT),
            dataDeceduto: currentDate.format(DATE_TIME_FORMAT),
            dataTerapiaIntensiva: currentDate.format(DATE_TIME_FORMAT),
            dataDimissione: currentDate.format(DATE_TIME_FORMAT),
            giorniIsolamentoDomiciliare: 1,
            giorniTerapiaIntensiva: 1,
            giorniGuarito: 1,
            giorniDeceduto: 1,
            nota: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataTampone: currentDate,
            dataGuarito: currentDate,
            dataIsolamentoDomiciliare: currentDate,
            dataDeceduto: currentDate,
            dataTerapiaIntensiva: currentDate,
            dataDimissione: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Paziente', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
