import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFarmaco } from 'app/shared/model/farmaco.model';

type EntityResponseType = HttpResponse<IFarmaco>;
type EntityArrayResponseType = HttpResponse<IFarmaco[]>;

@Injectable({ providedIn: 'root' })
export class FarmacoService {
  public resourceUrl = SERVER_API_URL + 'api/farmacos';

  constructor(protected http: HttpClient) {}

  create(farmaco: IFarmaco): Observable<EntityResponseType> {
    return this.http.post<IFarmaco>(this.resourceUrl, farmaco, { observe: 'response' });
  }

  update(farmaco: IFarmaco): Observable<EntityResponseType> {
    return this.http.put<IFarmaco>(this.resourceUrl, farmaco, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFarmaco>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFarmaco[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
