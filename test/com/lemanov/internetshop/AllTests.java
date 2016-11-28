package com.lemanov.internetshop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.lemanov.internetshop.domain.CatalogTest;
import com.lemanov.internetshop.domain.CustomerTest;
import com.lemanov.internetshop.domain.GoodsTest;
import com.lemanov.internetshop.domain.ServiceTest;


@RunWith(Suite.class)

@SuiteClasses({ CatalogTest.class, CustomerTest.class, GoodsTest.class, ServiceTest.class })
public class AllTests {}