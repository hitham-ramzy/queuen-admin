import { BaseEntity } from './../../shared';

export class Agent implements BaseEntity {
    constructor(
        public id?: number,
        public defaultBranchServiceId?: number,
        public branch?: BaseEntity,
    ) {
    }
}
