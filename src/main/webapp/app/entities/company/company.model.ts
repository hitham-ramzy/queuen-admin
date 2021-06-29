import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public logoUrl?: string,
        public active?: boolean,
        public createdAt?: any,
        public createdBy?: string,
        public lastDeactivationBySuperAdmin?: boolean,
        public branches?: BaseEntity[],
    ) {
        this.active = false;
        this.lastDeactivationBySuperAdmin = false;
    }
}
