import { BaseEntity, User } from './../../shared';

export class Vote implements BaseEntity {
    constructor(
        public id?: number,
        public matched?: boolean,
        public variants?: number,
        public createTS?: any,
        public updateTS?: any,
        public elector?: User,
        public candidate?: User,
        public dog?: BaseEntity,
    ) {
        this.matched = false;
    }
}
