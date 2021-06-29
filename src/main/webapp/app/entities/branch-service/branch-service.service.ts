import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { BranchService } from './branch-service.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BranchServiceService {

    private resourceUrl = SERVER_API_URL + 'api/branch-services';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(branchService: BranchService): Observable<BranchService> {
        const copy = this.convert(branchService);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(branchService: BranchService): Observable<BranchService> {
        const copy = this.convert(branchService);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<BranchService> {
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
     * Convert a returned JSON object to BranchService.
     */
    private convertItemFromServer(json: any): BranchService {
        const entity: BranchService = Object.assign(new BranchService(), json);
        entity.startingTime = this.dateUtils
            .convertDateTimeFromServer(json.startingTime);
        entity.endingTime = this.dateUtils
            .convertDateTimeFromServer(json.endingTime);
        entity.createdAt = this.dateUtils
            .convertDateTimeFromServer(json.createdAt);
        return entity;
    }

    /**
     * Convert a BranchService to a JSON which can be sent to the server.
     */
    private convert(branchService: BranchService): BranchService {
        const copy: BranchService = Object.assign({}, branchService);

        copy.startingTime = this.dateUtils.toDate(branchService.startingTime);

        copy.endingTime = this.dateUtils.toDate(branchService.endingTime);

        copy.createdAt = this.dateUtils.toDate(branchService.createdAt);
        return copy;
    }
}
