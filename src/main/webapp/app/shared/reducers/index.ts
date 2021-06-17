import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import objArte, {
  ObjArteState
} from 'app/entities/obj-arte/obj-arte.reducer';
// prettier-ignore
import artista, {
  ArtistaState
} from 'app/entities/artista/artista.reducer';
// prettier-ignore
import exhibicion, {
  ExhibicionState
} from 'app/entities/exhibicion/exhibicion.reducer';
// prettier-ignore
import pintura, {
  PinturaState
} from 'app/entities/pintura/pintura.reducer';
// prettier-ignore
import escultura, {
  EsculturaState
} from 'app/entities/escultura/escultura.reducer';
// prettier-ignore
import otroObj, {
  OtroObjState
} from 'app/entities/otro-obj/otro-obj.reducer';
// prettier-ignore
import collecPresta, {
  CollecPrestaState
} from 'app/entities/collec-presta/collec-presta.reducer';
// prettier-ignore
import collecPerma, {
  CollecPermaState
} from 'app/entities/collec-perma/collec-perma.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly objArte: ObjArteState;
  readonly artista: ArtistaState;
  readonly exhibicion: ExhibicionState;
  readonly pintura: PinturaState;
  readonly escultura: EsculturaState;
  readonly otroObj: OtroObjState;
  readonly collecPresta: CollecPrestaState;
  readonly collecPerma: CollecPermaState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  objArte,
  artista,
  exhibicion,
  pintura,
  escultura,
  otroObj,
  collecPresta,
  collecPerma,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
