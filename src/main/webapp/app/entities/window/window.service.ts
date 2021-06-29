import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Window } from './window.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WindowService {

    private resourceUrl = SERVER_API_URL + 'api/windows';

    constructor(private http: Http) { }

    create(window: Window): Observable<Window> {
        const copy = this.convert(window);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(window: Window): Observable<Window> {
        const copy = this.convert(window);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Window> {
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
     * Convert a returned JSON object to Window.
     */
    private convertItemFromServer(json: any): Window {
        const entity: Window = Object.assign(new Window(), json);
        return entity;
    }

    /**
     * Convert a Window to a JSON which can be sent to the server.
     */
    private convert(window: Window): Window {
        const copy: Window = Object.assign({}, window);
        return copy;
    }
}
