import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Queue } from './queue.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class QueueService {

    private resourceUrl = SERVER_API_URL + 'api/queues';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(queue: Queue): Observable<Queue> {
        const copy = this.convert(queue);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(queue: Queue): Observable<Queue> {
        const copy = this.convert(queue);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Queue> {
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
     * Convert a returned JSON object to Queue.
     */
    private convertItemFromServer(json: any): Queue {
        const entity: Queue = Object.assign(new Queue(), json);
        entity.day = this.dateUtils
            .convertDateTimeFromServer(json.day);
        return entity;
    }

    /**
     * Convert a Queue to a JSON which can be sent to the server.
     */
    private convert(queue: Queue): Queue {
        const copy: Queue = Object.assign({}, queue);

        copy.day = this.dateUtils.toDate(queue.day);
        return copy;
    }
}
