package cn.wagentim.work.controller;

import java.util.ArrayList;
import java.util.List;

import cn.wagentim.work.filter.ISelector;

public abstract class AbstractController implements IController
{

	protected List<ISelector> selectors = new ArrayList<ISelector>();

}
