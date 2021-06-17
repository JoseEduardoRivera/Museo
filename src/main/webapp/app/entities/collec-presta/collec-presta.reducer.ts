import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICollecPresta, defaultValue } from 'app/shared/model/collec-presta.model';

export const ACTION_TYPES = {
  FETCH_COLLECPRESTA_LIST: 'collecPresta/FETCH_COLLECPRESTA_LIST',
  FETCH_COLLECPRESTA: 'collecPresta/FETCH_COLLECPRESTA',
  CREATE_COLLECPRESTA: 'collecPresta/CREATE_COLLECPRESTA',
  UPDATE_COLLECPRESTA: 'collecPresta/UPDATE_COLLECPRESTA',
  PARTIAL_UPDATE_COLLECPRESTA: 'collecPresta/PARTIAL_UPDATE_COLLECPRESTA',
  DELETE_COLLECPRESTA: 'collecPresta/DELETE_COLLECPRESTA',
  RESET: 'collecPresta/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICollecPresta>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CollecPrestaState = Readonly<typeof initialState>;

// Reducer

export default (state: CollecPrestaState = initialState, action): CollecPrestaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COLLECPRESTA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COLLECPRESTA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COLLECPRESTA):
    case REQUEST(ACTION_TYPES.UPDATE_COLLECPRESTA):
    case REQUEST(ACTION_TYPES.DELETE_COLLECPRESTA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COLLECPRESTA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COLLECPRESTA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COLLECPRESTA):
    case FAILURE(ACTION_TYPES.CREATE_COLLECPRESTA):
    case FAILURE(ACTION_TYPES.UPDATE_COLLECPRESTA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COLLECPRESTA):
    case FAILURE(ACTION_TYPES.DELETE_COLLECPRESTA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLLECPRESTA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLLECPRESTA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COLLECPRESTA):
    case SUCCESS(ACTION_TYPES.UPDATE_COLLECPRESTA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COLLECPRESTA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COLLECPRESTA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/collec-prestas';

// Actions

export const getEntities: ICrudGetAllAction<ICollecPresta> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COLLECPRESTA_LIST,
    payload: axios.get<ICollecPresta>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICollecPresta> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COLLECPRESTA,
    payload: axios.get<ICollecPresta>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICollecPresta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COLLECPRESTA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICollecPresta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COLLECPRESTA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICollecPresta> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COLLECPRESTA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICollecPresta> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COLLECPRESTA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
