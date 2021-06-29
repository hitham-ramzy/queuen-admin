import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueueNSharedModule } from '../../shared';
import {
    BranchServiceService,
    BranchServicePopupService,
    BranchServiceComponent,
    BranchServiceDetailComponent,
    BranchServiceDialogComponent,
    BranchServicePopupComponent,
    BranchServiceDeletePopupComponent,
    BranchServiceDeleteDialogComponent,
    branchServiceRoute,
    branchServicePopupRoute,
    BranchServiceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...branchServiceRoute,
    ...branchServicePopupRoute,
];

@NgModule({
    imports: [
        QueueNSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BranchServiceComponent,
        BranchServiceDetailComponent,
        BranchServiceDialogComponent,
        BranchServiceDeleteDialogComponent,
        BranchServicePopupComponent,
        BranchServiceDeletePopupComponent,
    ],
    entryComponents: [
        BranchServiceComponent,
        BranchServiceDialogComponent,
        BranchServicePopupComponent,
        BranchServiceDeleteDialogComponent,
        BranchServiceDeletePopupComponent,
    ],
    providers: [
        BranchServiceService,
        BranchServicePopupService,
        BranchServiceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueueNBranchServiceModule {}
