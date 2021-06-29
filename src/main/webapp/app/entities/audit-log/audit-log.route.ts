import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AuditLogComponent } from './audit-log.component';
import { AuditLogDetailComponent } from './audit-log-detail.component';
import { AuditLogPopupComponent } from './audit-log-dialog.component';
import { AuditLogDeletePopupComponent } from './audit-log-delete-dialog.component';

@Injectable()
export class AuditLogResolvePagingParams implements Resolve<any> {

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

export const auditLogRoute: Routes = [
    {
        path: 'audit-log',
        component: AuditLogComponent,
        resolve: {
            'pagingParams': AuditLogResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.auditLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'audit-log/:id',
        component: AuditLogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.auditLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auditLogPopupRoute: Routes = [
    {
        path: 'audit-log-new',
        component: AuditLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.auditLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'audit-log/:id/edit',
        component: AuditLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.auditLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'audit-log/:id/delete',
        component: AuditLogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.auditLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
