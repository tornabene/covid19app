import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISintomo } from 'app/shared/model/sintomo.model';

type EntityResponseType = HttpResponse<ISintomo>;
type EntityArrayResponseType = HttpResponse<ISintomo[]>;

@Injectable({ providedIn: 'root' })
export class SintomoService {
  public resourceUrl = SERVER_API_URL + 'api/sintomos';

  constructor(protected http: HttpClient) {}

  create(sintomo: ISintomo): Observable<EntityResponseType> {
    return this.http.post<ISintomo>(this.resourceUrl, sintomo, { observe: 'response' });
  }

  update(sintomo: ISintomo): Observable<EntityResponseType> {
    return this.http.put<ISintomo>(this.resourceUrl, sintomo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISintomo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISintomo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
