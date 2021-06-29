import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AdminComponent } from './admin.component';
import { AdminDetailComponent } from './admin-detail.component';
import { AdminPopupComponent } from './admin-dialog.component';
import { AdminDeletePopupComponent } from './admin-delete-dialog.component';

@Injectable()
export class AdminResolvePagingParams implements Resolve<any> {

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

export const adminRoute: Routes = [
    {
        path: 'admin',
        component: AdminComponent,
        resolve: {
            'pagingParams': AdminResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.admin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'admin/:id',
        component: AdminDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.admin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adminPopupRoute: Routes = [
    {
        path: 'admin-new',
        component: AdminPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.admin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'admin/:id/edit',
        component: AdminPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.admin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'admin/:id/delete',
        component: AdminDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.admin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
