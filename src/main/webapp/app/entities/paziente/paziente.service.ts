import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaziente } from 'app/shared/model/paziente.model';

type EntityResponseType = HttpResponse<IPaziente>;
type EntityArrayResponseType = HttpResponse<IPaziente[]>;

@Injectable({ providedIn: 'root' })
export class PazienteService {
  public resourceUrl = SERVER_API_URL + 'api/pazientes';

  constructor(protected http: HttpClient) {}

  create(paziente: IPaziente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paziente);
    return this.http
      .post<IPaziente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paziente: IPaziente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paziente);
    return this.http
      .put<IPaziente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaziente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaziente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paziente: IPaziente): IPaziente {
    const copy: IPaziente = Object.assign({}, paziente, {
      dataTampone: paziente.dataTampone && paziente.dataTampone.isValid() ? paziente.dataTampone.toJSON() : undefined,
      dataGuarito: paziente.dataGuarito && paziente.dataGuarito.isValid() ? paziente.dataGuarito.toJSON() : undefined,
      dataIsolamentoDomiciliare:
        paziente.dataIsolamentoDomiciliare && paziente.dataIsolamentoDomiciliare.isValid()
          ? paziente.dataIsolamentoDomiciliare.toJSON()
          : undefined,
      dataDeceduto: paziente.dataDeceduto && paziente.dataDeceduto.isValid() ? paziente.dataDeceduto.toJSON() : undefined,
      dataTerapiaIntensiva:
        paziente.dataTerapiaIntensiva && paziente.dataTerapiaIntensiva.isValid() ? paziente.dataTerapiaIntensiva.toJSON() : undefined,
      dataDimissione: paziente.dataDimissione && paziente.dataDimissione.isValid() ? paziente.dataDimissione.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataTampone = res.body.dataTampone ? moment(res.body.dataTampone) : undefined;
      res.body.dataGuarito = res.body.dataGuarito ? moment(res.body.dataGuarito) : undefined;
      res.body.dataIsolamentoDomiciliare = res.body.dataIsolamentoDomiciliare ? moment(res.body.dataIsolamentoDomiciliare) : undefined;
      res.body.dataDeceduto = res.body.dataDeceduto ? moment(res.body.dataDeceduto) : undefined;
      res.body.dataTerapiaIntensiva = res.body.dataTerapiaIntensiva ? moment(res.body.dataTerapiaIntensiva) : undefined;
      res.body.dataDimissione = res.body.dataDimissione ? moment(res.body.dataDimissione) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paziente: IPaziente) => {
        paziente.dataTampone = paziente.dataTampone ? moment(paziente.dataTampone) : undefined;
        paziente.dataGuarito = paziente.dataGuarito ? moment(paziente.dataGuarito) : undefined;
        paziente.dataIsolamentoDomiciliare = paziente.dataIsolamentoDomiciliare ? moment(paziente.dataIsolamentoDomiciliare) : undefined;
        paziente.dataDeceduto = paziente.dataDeceduto ? moment(paziente.dataDeceduto) : undefined;
        paziente.dataTerapiaIntensiva = paziente.dataTerapiaIntensiva ? moment(paziente.dataTerapiaIntensiva) : undefined;
        paziente.dataDimissione = paziente.dataDimissione ? moment(paziente.dataDimissione) : undefined;
      });
    }
    return res;
  }
}
