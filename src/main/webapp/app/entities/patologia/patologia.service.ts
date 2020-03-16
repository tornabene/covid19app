import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatologia } from 'app/shared/model/patologia.model';

type EntityResponseType = HttpResponse<IPatologia>;
type EntityArrayResponseType = HttpResponse<IPatologia[]>;

@Injectable({ providedIn: 'root' })
export class PatologiaService {
  public resourceUrl = SERVER_API_URL + 'api/patologias';

  constructor(protected http: HttpClient) {}

  create(patologia: IPatologia): Observable<EntityResponseType> {
    return this.http.post<IPatologia>(this.resourceUrl, patologia, { observe: 'response' });
  }

  update(patologia: IPatologia): Observable<EntityResponseType> {
    return this.http.put<IPatologia>(this.resourceUrl, patologia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPatologia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPatologia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
