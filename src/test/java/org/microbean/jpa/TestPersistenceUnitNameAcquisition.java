/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2018 microBean.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.microbean.jpa;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;

import javax.enterprise.event.Observes;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import javax.persistence.spi.PersistenceUnitInfo;

import javax.sql.DataSource;

import javax.transaction.Transaction;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.transaction.UserTransaction;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import static org.microbean.main.Main.main;

@ApplicationScoped
public class TestPersistenceUnitNameAcquisition {

  @Inject
  private UserTransaction injectedUserTransaction;

  @Inject
  private Transaction injectedTransaction;
  
  @Inject
  private TestPersistenceUnitNameAcquisition self;

  @PersistenceContext(unitName = "test")
  private EntityManager testEm;

  @Inject
  private DataSource test;
  
  public TestPersistenceUnitNameAcquisition() {
    super();
  }

  private final void onStartup(@Observes @Initialized(ApplicationScoped.class) final Object event, final UserTransaction userTransaction) {
    assertNotNull(userTransaction);
    assertNotNull(this.injectedTransaction);
    assertNotNull(this.injectedUserTransaction);
    assertNotNull(this.self);
    assertNotNull(this.test);
    assertNotNull(this.testEm);
    this.self.frobnicate();
  }

  @Transactional(TxType.REQUIRED)
  public void frobnicate() {
    System.out.println("*** frobnicating");
    System.out.println("*** injected UserTransaction: " + this.injectedUserTransaction);
    System.out.println("*** injected transaction: " + this.injectedTransaction);
  }

  @Test
  public void testIt() throws Exception {
    main(null);
  }
  
}
