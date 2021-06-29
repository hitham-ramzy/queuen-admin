import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SessionComponent } from './session.component';
import { SessionDetailComponent } from './session-detail.component';
import { SessionPopupComponent } from './session-dialog.component';
import { SessionDeletePopupComponent } from './session-delete-dialog.component';

@Injectable()
export class SessionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const sessionRoute: Routes = [
    {
        path: 'session',
        component: SessionComponent,
        resolve: {
            'pagingParams': SessionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.session.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'session/:id',
        component: SessionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.session.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sessionPopupRoute: Routes = [
    {
        path: 'session-new',
        component: SessionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.session.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'session/:id/edit',
        component: SessionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.session.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'session/:id/delete',
        component: SessionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.session.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
