import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lead.reducer';

export const LeadDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leadEntity = useAppSelector(state => state.lead.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leadDetailsHeading">
          <Translate contentKey="leadManagementApp.lead.detail.title">Lead</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{leadEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="leadManagementApp.lead.name">Name</Translate>
            </span>
          </dt>
          <dd>{leadEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="leadManagementApp.lead.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{leadEntity.phone}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="leadManagementApp.lead.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{leadEntity.createdAt ? <TextFormat value={leadEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="leadManagementApp.lead.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{leadEntity.createdBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="leadManagementApp.lead.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{leadEntity.updatedAt ? <TextFormat value={leadEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="leadManagementApp.lead.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{leadEntity.updatedBy}</dd>
        </dl>
        <Button tag={Link} to="/lead" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lead/${leadEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeadDetail;
