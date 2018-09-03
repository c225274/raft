package com.github.c225274.raft.core;

import org.apache.log4j.Logger;


public class RaftNode {
    
    private static final Logger loggger =Logger.getLogger(RaftNode.class);
    
    private NodeState nodeState=NodeState.FOLLOWER;
    
}
