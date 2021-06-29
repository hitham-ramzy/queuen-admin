import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { QueueComponent } from './queue.component';
import { QueueDetailComponent } from './queue-detail.component';
import { QueuePopupComponent } from './queue-dialog.component';
import { QueueDeletePopupComponent } from './queue-delete-dialog.component';

@Injectable()
export class QueueResolvePagingParams implements Resolve<any> {

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

export const queueRoute: Routes = [
    {
        path: 'queue',
        component: QueueComponent,
        resolve: {
            'pagingParams': QueueResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'queue/:id',
        component: QueueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const queuePopupRoute: Routes = [
    {
        path: 'queue-new',
        component: QueuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'queue/:id/edit',
        component: QueuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'queue/:id/delete',
        component: QueueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
