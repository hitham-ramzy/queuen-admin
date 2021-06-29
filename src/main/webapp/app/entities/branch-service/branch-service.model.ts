import { BaseEntity } from './../../shared';

export class BranchService implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public startingTime?: any,
        public endingTime?: any,
        public averageServingMinutes?: number,
        public createdAt?: any,
        public createdBy?: string,
        public active?: boolean,
        public lastDeactivationBySuperAdmin?: boolean,
        public windows?: BaseEntity[],
        public branch?: BaseEntity,
    ) {
        this.active = false;
        this.lastDeactivationBySuperAdmin = false;
    }
}
