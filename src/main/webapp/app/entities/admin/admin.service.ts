import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Admin } from './admin.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AdminService {

    private resourceUrl = SERVER_API_URL + 'api/admins';

    constructor(private http: Http) { }

    create(admin: Admin): Observable<Admin> {
        const copy = this.convert(admin);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(admin: Admin): Observable<Admin> {
        const copy = this.convert(admin);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Admin> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Admin.
     */
    private convertItemFromServer(json: any): Admin {
        const entity: Admin = Object.assign(new Admin(), json);
        return entity;
    }

    /**
     * Convert a Admin to a JSON which can be sent to the server.
     */
    private convert(admin: Admin): Admin {
        const copy: Admin = Object.assign({}, admin);
        return copy;
    }
}
