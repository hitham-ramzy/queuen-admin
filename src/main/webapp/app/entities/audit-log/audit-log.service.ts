import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AuditLog } from './audit-log.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AuditLogService {

    private resourceUrl = SERVER_API_URL + 'api/audit-logs';

    constructor(private http: Http) { }

    create(auditLog: AuditLog): Observable<AuditLog> {
        const copy = this.convert(auditLog);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(auditLog: AuditLog): Observable<AuditLog> {
        const copy = this.convert(auditLog);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AuditLog> {
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
     * Convert a returned JSON object to AuditLog.
     */
    private convertItemFromServer(json: any): AuditLog {
        const entity: AuditLog = Object.assign(new AuditLog(), json);
        return entity;
    }

    /**
     * Convert a AuditLog to a JSON which can be sent to the server.
     */
    private convert(auditLog: AuditLog): AuditLog {
        const copy: AuditLog = Object.assign({}, auditLog);
        return copy;
    }
}
