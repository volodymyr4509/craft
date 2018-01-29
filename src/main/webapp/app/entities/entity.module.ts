import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CraftDogModule } from './dog/dog.module';
import { CraftVoteModule } from './vote/vote.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CraftDogModule,
        CraftVoteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftEntityModule {}
